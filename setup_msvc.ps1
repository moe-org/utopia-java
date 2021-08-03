# Part of the UtopiaServer Project, Use MIT License

# 对于github action
# 设置编译环境
Write-Host "设置msvc环境"
Write-Host "警告:仅在CI/CD环境下使用"

# 检查参数
if($args.Count -ne 3){
    Write-Error "参数错误"
    exit 1
}

# 获取vcvars64.bat
$vs_install_dir = &"${env:ProgramFiles(x86)}\Microsoft Visual Studio\Installer\vswhere.exe" -latest -property installationPath

$msvc_cmd = "$vs_install_dir\VC\Auxiliary\Build\vcvars64.bat"

# 获取参数
$build_type = $args[0]
$pack_type = $args[1]
$test_type = $args[2]

# 设置环境并启动build.ps1
&"cmd.exe" "/c" "`"$msvc_cmd`" && pwsh.exe -c `"./Build.ps1 ${build_type} ${pack_type} ${test_type}`""
