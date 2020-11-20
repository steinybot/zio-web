---
id: getting_started
title: "Getting Started"
---

Include ZIO in your project by adding the following to your `build.sbt` file:

```scala mdoc:passthrough
println(s"""```""")
if (zio.BuildInfo.isSnapshot)
  println(s"""resolvers += Resolver.sonatypeRepo("snapshots")""")
println(s"""libraryDependencies += "dev.zio" %% "zio-web" % "${zio.BuildInfo.version}"""")
println(s"""```""")
```

## Main

TODO
