plugins {
    id("dev.kikugie.stonecutter")
    id("co.uzzu.dotenv.gradle") version "4.0.0"
    id("dev.isxander.modstitch.base") version "0.8.4" apply false
}

stonecutter active file("versions/current")
