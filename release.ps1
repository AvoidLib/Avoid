if ($ENV:GITHUB_TOKEN -eq $null) {
    echo "[Error] Please specify the $$ENV:GITHUB_TOKEN"
    exit 1
}

if ($ENV:MODRINTH_API_KEY -eq $null) {
    echo "[Error] Please specify the $$ENV:MODRINTH_API_KEY"
    exit 1
}

./gradlew publishGitHub -Dgen_sources=1 | out-null

$jar = python -c "import os; print([k for k in os.listdir('build/libs') if '+' not in k and '-sources.jar' in k][0])"

echo "[--- github published ---]"
echo "sources jar :: $jar";
echo "";

echo "[--- publishing versions ---]"
funcutter considerRelease --no-configuration-cache
