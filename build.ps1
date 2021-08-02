# Part of UtopiaServer Project
# Use MIT License
# 构建脚本

$home_dir = Get-Location

# 构建失败以退出函数
function failed_exit(){
    Write-Host "Exit" -ForegroundColor Red
    Set-Location $home_dir
    exit 1
}

# 构建成功以成功函数
function well_done(){
    Write-Host "All done" -ForegroundColor Green
    Set-Location $home_dir
    exit 0
}

# 检查程序是否存在
function test-program($prog_name){
    if ($null -eq (Get-Command $prog_name -ErrorAction SilentlyContinue)) 
    { 
        Write-Error "Checking ${prog_name} ... no!"

        # 退出，返回开始的地方
        failed_exit
    }
    else{
        Write-Output "Checking ${prog_name} ... ok!"
    }
}

# 处理命令结果
function test-command($result){
    if($result -ne "True"){
        Write-Error "Build failed down:$result"

        # 退出，返回开始的地方
        failed_exit
    }
}

# 删除目录(如果存在)
function clean_dir($dir){
    if(Test-Path -Path "${dir}"){
        Write-Output "Remove the ${dir} directory"
        Remove-Item -Path "${dir}" -Force -Recurse
    }
}

# 检查参数
# args[0] - 支持Debug和Release两种参数
# args[1] - 支持Pack和Nopack两种参数
$build_type
$pack_type

if($args.Count -ne 2){
    Write-Error "Miss or too many parameters"
    Write-Error "Accepted two parameters:"
    Write-Error "Accepted parameter 1:'Debug' or 'Release'"
    Write-Error "Accepted parameter 2:'Pack' or 'Nopack'"
    Write-Error "Such as './build.ps1 Debug Nopack'"
    failed_exit
}

# 检查构建类型
if($args[0] -eq "Debug"){
    $build_type = "Debug"
}
elseif($args[0] -eq "Release"){
    $build_type = "Release"
} 
else{
    Write-Error "Unknown parameter:" + $args[0]
    Write-Error "Accepted parameters:'Debug' or 'Release'"
    failed_exit
}

# 检查打包类型
if($args[1] -eq "Pack"){
    $pack_type = "Pack"
}
elseif($args[1] -eq "Nopack"){
    $pack_type = "Nopack"
}
else{
    Write-Error "Unknown parameter:" + $args[1]
    Write-Error "Accepted parameters:'Pack' or 'Nopack'"
    failed_exit
}

Write-Host "Build ${build_type} -> ${pack_type}" -ForegroundColor Green

# 检查
# cmake
# cpack
Write-Output "Checking depends ..."
test-program("cmake")
test-program("cpack")

# 删除老的构建缓存目录
clean_dir("./build")
clean_dir("./package")

# 新建一个build目录
New-Item -ItemType Directory -Path "./build" -Force
Set-Location "./build"

# 获取cpu线程数
$build_thread_count = [System.Environment]::ProcessorCount;

# 构建
&"cmake" ".." "-D" "CMAKE_BUILD_TYPE=${build_type}"
test-command($?)
&"cmake" "--build" "." "--config" "${build_type}" "-j" "${build_thread_count}"
test-command($?)

Write-Host " - Build success -" -ForegroundColor Green

# 打包
if($pack_type -eq "Pack"){
    &"cpack" "--config" "CPackConfig.cmake" "-D" "CMAKE_INSTALL_CONFIG_NAME=${build_type}" "-D" "CPACK_BUILD_CONFIG=${build_type}"
    test-command($?)

    &"cpack" "--config" "CPackSourceConfig.cmake" "-D" "CMAKE_INSTALL_CONFIG_NAME=${build_type}" "-D" "CPACK_BUILD_CONFIG=${build_type}"
    test-command($?)

    Write-Host " - pack done -" -ForegroundColor Green
}
else{
    Write-Host " - skip pack -" -ForegroundColor Green
}

# 完成
well_done
