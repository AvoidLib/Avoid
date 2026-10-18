package main

import "strings"
import "os"

func main() {
    bytes, err := os.ReadFile("build.gradle")
    if err != nil {
        panic(err)
    }

    content := string(bytes)
    content = content[:strings.Index(content, "//#region limepublish")] +
              content[strings.Index(content, "//#region limepublishEnd") + 24:]

    err = os.WriteFile("build.gradle", []byte(content), 0644)
    if err != nil {
        panic(err)
    }
}
