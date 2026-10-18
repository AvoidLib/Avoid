$Env:GOOS = "linux"; $Env:GOARCH = "amd64"

mkdir bin -ErrorAction Ignore

function build()
{
    cd scripts/$args
    & "${ENV:USERPROFILE}\source\repos\GoPowered\bin\Release\net10.0\GoPowered.exe" build
    cd .gopowered/compile/go
    go build -buildvcs=false
    cd ../../..
    cd ../..
    move scripts/$args/.gopowered/compile/go/go ./bin/$args -Force
    git add ./bin/$args
}

build removeLime
build getjar
