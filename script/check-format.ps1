# Part of the UtopiaServer Project, Use MIT License
# 此脚本用于检查C++代码格式是否符合标准
# Powered by clang-format


# 创建clang-format进程并获取其输出
function createClangFormat($file_path){
    $process = [System.Diagnostics.Process]::new()

    $process.StartInfo.FileName = "clang-format"
    $process.StartInfo.Arguments = @("-style=file",$file_path)
    $process.StartInfo.UseShellExecute = $false
    $process.StartInfo.RedirectStandardOutput = $true
    $process.StartInfo.RedirectStandardError = $false
    $process.StartInfo.RedirectStandardInput = $false
    $process.StartInfo.CreateNoWindow = $true
    $process.Start()

    $process.WaitForExit()
    $output = $process.StandardOutput.ReadToEnd()
    $process.Close()

    return $output
}

# 检查参数数量
if($args.Count -ne 2){
    Write-Error "需要两个参数!"
    Write-Error "一个参数指定目标目录"
    Write-Error "另一个boolean参数指定是否原地格式化写入"
    Write-Error "如果写入则不警告"
    exit 1
}

# 解析源目录
$source_files = Get-ChildItem -Recurse -Force -Path $args[0] -Attributes !Directory

# 解析是否修改
$is_write = [System.Boolean]::Parse($args[1])

# 警告列表
$warn_list = @()

# 挨个检查
foreach($file in $source_files){
    # 判断是否为源文件
    # 以.cpp .cc .hpp .h .hxx .cxx .c++ .h++结尾
    $strs = [System.String]::Copy($file.Name).ToLower()

    # 不是源文件就跳过
    if(!($strs.EndsWith(".cpp")     -or 
         $strs.EndsWith(".cc")      -or 
         $strs.EndsWith(".hpp")     -or 
         $strs.EndsWith(".h")       -or 
         $strs.EndsWith(".hxx")     -or 
         $strs.EndsWith(".cxx")     -or 
         $strs.EndsWith(".c++")     -or 
         $strs.EndsWith(".h++"))){
        Write-Host "跳过文件:${file}"
        continue
    }

    # 检查是否写入
    if($is_write){
        # 原地写入
        &"clang-format" "-style=file" "-i" $file.FullName
    }
    else{
        # 否则就检查格式
        Write-Host "检查文件:${file}"

        # 检查格式化输出
        #                                   跳过结果，直接获取数组    ↓
        $clang_format_output = (createClangFormat $file.FullName)[1]
        $source = [System.IO.File]::ReadAllText($file.FullName)

        # clang-format结果不等于源文件就警告
        if(!($source.Equals($clang_format_output))){
            $warn_list += $file.FullName

            Write-Host "检查完成 - 不符合格式"
        }
        else{
            Write-Host "检查完成 - 正确"
        }
    }
}

# 如果警告列表不为空则输出警告
# 并且返回1
if($warn_list){
    Write-Warning "以下文件不符合格式:"

    foreach($file in $warn_list){
        Write-Warning $file
    }

    exit 1
}
else{
    exit 0
}
