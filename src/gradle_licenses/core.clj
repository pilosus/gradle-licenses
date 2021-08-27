(ns gradle-licenses.core
  "Parse JSON generated by gradle-license-plugin into Clojure native data structure"
  (:gen-class)
  (:require
   ;;[clojure.spec.test.alpha :refer [instrument]]
   [cheshire.core :as json]
   [clojure.spec.alpha :as s]
   [clojure.string :as str]))

;; Specs

(s/def ::str string?)
(s/def ::nilable-str (s/nilable string?))

(s/def ::package ::nilable-str)
(s/def ::license ::nilable-str)
(s/def ::path string?)
(s/def ::options (s/map-of keyword? any?))
(s/def ::dependency (s/map-of string? any?))
(s/def ::license (s/cat
                  :license ::str
                  :license_url ::str))
(s/def ::license-vec (s/* ::license))

(s/def ::data
  (s/cat :package ::package :license ::license))


;; Const


(def key-dependency "dependency")
(def key-licenses "licenses")
(def key-license "license")

;; Helpers

(s/fdef path->string
  :args ::path
  :ret ::str)

(defn path->string
  "Return a string with the file contents"
  [path]
  (slurp path))

(s/fdef license-vec->license
  :args ::license-vec
  :ret ::nilable-str)

(defn license-vec->license
  "Convert a vector of licenses into nilable license string"
  [license-vec]
  (let [license-names (map #(get % key-license) license-vec)
        license-str (str/join ", " license-names)
        result (not-empty license-str)]
    result))

(s/fdef dependency->map
  :args ::dependency
  :ret ::data)

(defn dependency->map
  "Convert dependency item from the parsed JSON into a map"
  [dependency]
  (let [package-str (get dependency key-dependency)
        license-vec (get dependency key-licenses)
        license-str (license-vec->license license-vec)]
    {:package package-str :license license-str}))

;; Entrypoint

(s/fdef gradle-json->data
  :args (s/cat
         :path ::path
         :options ::options)
  :ret (s/* ::data))

(defn gradle-json->data
  "Parse gradle-license-plugin JSON into vector of {:package PACKAGE :license LICENSE} maps"
  #_:clj-kondo/ignore
  [path options]
  (let [content (path->string path)
        dependencies (json/parse-string content)
        result (map dependency->map dependencies)]
    result))

;; (instrument `gradle-json->data)