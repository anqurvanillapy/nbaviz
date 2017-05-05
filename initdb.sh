#!/usr/bin/env bash

app=nbaviz

# TODO: Avoid assuming them in $PATH.
initdb $app-db
postgres -D $app-db &
createdb $app
