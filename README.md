# gradle-licenses

[![codecov](https://codecov.io/gh/pilosus/gradle-licenses/branch/master/graph/badge.svg?token=fIDPrOvUru)](https://codecov.io/gh/pilosus/gradle-licenses)
[![Clojars Version](https://img.shields.io/clojars/v/org.clojars.vrs/gradle-licenses)](https://clojars.org/org.clojars.vrs/gradle-licenses)

A Clojure library for parsing [gradle-license-plugin](https://github.com/jaredsburrows/gradle-license-plugin)
JSON files to be used in [pip-license-checker](https://github.com/pilosus/pip-license-checker)
license compliance tool.
x
## Installation

### Leiningen

Add the following to the `:dependencies` list in your `project.clj`:

```
[org.clojars.vrs/gradle-licenses "0.1.0"]
```

## Quick Start

```
(require '[gradle-licenses.core :refer [gradle-json->data]])
(gradle-json->data "resources/gradle.json" {})
```

## Options

No options implemented yet.


## License

Copyright © 2021 Vitaly Samigullin

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
