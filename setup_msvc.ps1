# Part of the UtopiaServer Project, Use MIT License

# 对于github action
# 设置编译环境
Write-Host "设置msvc环境"
Write-Host "警告:仅在CI/CD环境下使用"

# 获取vcvars64.bat
$vs_install_dir = &"${env:ProgramFiles(x86)}\Microsoft Visual Studio\Installer\vswhere.exe" -latest -property installationPath

$msvc_cmd = "$vs_install_dir\VC\Auxiliary\Build\vcvars64.bat"

$cmd_args

foreach($arg in $args){
    $cmd_args += $arg
}

# 设置环境并启动build.ps1
&"cmd.exe" "/c" "`"$msvc_cmd`" && python ./build.py ${cmd_args}"

exit $?
