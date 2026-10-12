$sources = Get-ChildItem -Path "src" -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
javac --release 17 -encoding UTF-8 -cp "lib\*" -d "build\classes" $sources
