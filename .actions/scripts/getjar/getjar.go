package main

import "strings"
import "fmt"
import "os"

func main() {
    fp, err := os.Open("build/libs")
    if err != nil {
        panic(err)
    }

    defer fp.Close()

    names, err := fp.Readdirnames(0)
    if err != nil {
        panic(err)
    }

    for _, name := range names {
        if !strings.Contains(name, "-sources") {
            fmt.Print(name[:len(name) - 4])
            return
        }
    }

    os.Exit(1)
}
