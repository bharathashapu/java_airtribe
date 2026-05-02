# What is JDK, JRE, JVM
## JVM - Java Virtual Machine

JVM is an engine which runs the Java program.
It takes the complied java code (bytecode) and executes it.
Every OS has it's own JVM implementation
but each one understands the same bytecode.

## JRE - Java Runtime Environment

JRE = JVM + the standard libraries
All built-in classes your program relies on.
It's everything you need to run a Java program but
not to write or compile one. If a user wants to run
a Java application, they only need JRE.

## JDK - Java Development Kit
It is a developer toolbox. JDK = JRE + development tools like compiler,
debugger, archiver, documentation and more tools to write Java programs.

```
┌──────────────────────────────────────────┐
│  JDK                                     │
│  ┌────────────────────────────────────┐  │
│  │  JRE                               │  │
│  │  ┌────────────────────────────┐    │  │
│  │  │  JVM                       │    │  │
│  │  └────────────────────────────┘    │  │
│  │  + Core Class Libraries            │  │
│  └────────────────────────────────────┘  │
│  + Development Tools (javac, jdb, ...)   │
└──────────────────────────────────────────┘
```

# What is bytecode

When you run `javac Hello.java`, the compiler doesn't produce machine code,
it produces a `Hello.class` file containing bytecode. Bytecode is a
compact, platform-independent set of instructions designed for the JVM.

# What does “write once, run anywhere” mean (1–2 short paragraphs)

This phrase, often abbreviated WORA, is Java's original promise: you
compile your source code once into bytecode, and that same bytecode runs
on any operating system or hardware that has a compatible JVM.