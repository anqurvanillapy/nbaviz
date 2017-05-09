# nbaviz

NBA stats and visualization app.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To init a local database (PostgreSQL), run `initdb.sh`:

```bash
$ bash initdb.sh
# Mainly the following three commands:
#   initdb nbaviz-db
#   postgres -D nbaviz-db &
#   createdb nbaviz
```

To start a web server for the application, run:

```bash
$ lein ring server
```

## License

MIT
