# 对于github action
# 设置编译环境
Write-Host "Set up the msvc"
Write-Host "Use for CI/CD"

if($args.Count -ne 3){
    Write-Error "The arg set wrong."
    Write-Error "If doesn't on CI/CD environment. Don't use this script"
    exit 1
}

$vs_install_dir = &"${env:ProgramFiles(x86)}\Microsoft Visual Studio\Installer\vswhere.exe" -latest -property installationPath

$msvc_cmd = "$vs_install_dir\VC\Auxiliary\Build\vcvars64.bat"

$build_type = $args[0]
$pack_type = $args[1]
$test_type = $args[2]

&"cmd.exe" "/c" "`"$msvc_cmd`" && pwsh.exe -c `"./Build.ps1 ${build_type} ${pack_type} ${test_type}`""
