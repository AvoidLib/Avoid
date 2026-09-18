$Env:GOOS = "linux"; $Env:GOARCH = "amd64"
& "${ENV:USERPROFILE}\source\repos\GoPowered\bin\Release\net10.0\GoPowered.exe" build
cd .gopowered/compile/go
go build -buildvcs=false
cd ../../..
mkdir bin -ErrorAction Ignore
move .gopowered/compile/go/go ./bin/removeLime -Force
git add ./bin/removeLime
