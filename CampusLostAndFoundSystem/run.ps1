Push-Location $PSScriptRoot
try {
    if (-not (Test-Path -LiteralPath 'build\classes\campus\lost\and\found\system\LoginForm.class')) {
        & "$PSScriptRoot\compile.ps1"
    }
    java '-Dfile.encoding=UTF-8' -cp 'build\classes;lib\mysql-connector-java-8.0.25.jar' campus.lost.and.found.system.LoginForm
} finally {
    Pop-Location
}
