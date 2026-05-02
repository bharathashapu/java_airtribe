# 1. JDK version used

```commandline
java -version
```
```
openjdk version "25.0.1" 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-25.0.1+8 (build 25.0.1+8-LTS)
OpenJDK 64-Bit Server VM Temurin-25.0.1+8 (build 25.0.1+8-LTS, mixed mode, sharing)
```

# 2. Screenshots or brief explanation of “Hello World” program run.

1. Create "Hello.java"

```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

2. Compile and Run

```commandline
javac Hello.java
java Hello
```

3. Expected Output

```commandline
Hello, World!
```