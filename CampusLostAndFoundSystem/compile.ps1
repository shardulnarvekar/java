$ErrorActionPreference = 'Stop'
Push-Location $PSScriptRoot
try {
    New-Item -ItemType Directory -Path 'build\classes' -Force | Out-Null
    $campusSources = Get-ChildItem -LiteralPath 'src' -Recurse -Filter '*.java' | ForEach-Object FullName
    javac --release 17 -encoding UTF-8 -cp 'lib\mysql-connector-java-8.0.25.jar' -d 'build\classes' $campusSources
    if ($LASTEXITCODE -ne 0) { throw 'Compilation failed.' }
} finally {
    Pop-Location
}
