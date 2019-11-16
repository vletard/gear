#!/bin/bash

find . -name "*.class" -exec rm -f {} \;
javac analogy/Main.java && java analogy.Main
