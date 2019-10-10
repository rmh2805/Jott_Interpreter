#!/bin/bash
for f in ./jottExamples/*
do
    printf "\nInterpreting $f file..."
    java test.testTokenizer $f
    read -n 1 -p "\nPress any key to continue to next file."
done