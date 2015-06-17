# Development Roadmap #

This page describes the plan of future versions for the project. These plans are just reminders for myself to remember what I have to do and in what order. The roadmap does not contains the release dates as the work I put in this project is depends on my mood and free time.

## 0.5.2 ##

As I decided to port the project to JFace, which means a complete rewrite from scratch, after version 0.5.1 (plus some fixed issue in the SVN trunk) this branch is discontinued. Every open issue labeled to this release will be delayed until the JFace port is done with version 0.6.

## 0.6.0 ##

Basic functionality using JFace API. This means a subset of the functionality of 0.5.0, as some features will be intentionally left out, for example I do not plan to reimplement the filters function in this version, as I plan to redesign it. The core features which should be working in this version, and issues to be fixed:

  * Migrating to JFace API (see [issue13](https://code.google.com/p/debugvisualisation/issues/detail?id=13))
  * Open/close individual nodes (showing, hiding child nodes and primitive-typed variables as multi-line node)
  * Hide/show nodes
  * Handling some hotkeys (see [issue7](https://code.google.com/p/debugvisualisation/issues/detail?id=7))
  * Some javadoc issues ([issue10](https://code.google.com/p/debugvisualisation/issues/detail?id=10), [issue15](https://code.google.com/p/debugvisualisation/issues/detail?id=15))
  * [issue6](https://code.google.com/p/debugvisualisation/issues/detail?id=6), if it still stands for the JFace version

## 0.6.1 ##

This version should reach the functionality of the 0.5.x trunk version:

  * Better automatic filtering:
    * option to disable/enable specific filters
    * group filters (enable/disable filter groups)
    * user-defined filters
    * pre-defined filter groups for basic java objects, EMF ([issue8](https://code.google.com/p/debugvisualisation/issues/detail?id=8)), etc
  * cooperate with Variables View

## 0.6.2 ##

I've put enhancements here, which I don't know how to begin as I did not do any research on them, and won't do until other, more important things are done.

  * Open source file ([issue11](https://code.google.com/p/debugvisualisation/issues/detail?id=11))
  * Start graph from another node than the local context ([issue4](https://code.google.com/p/debugvisualisation/issues/detail?id=4))