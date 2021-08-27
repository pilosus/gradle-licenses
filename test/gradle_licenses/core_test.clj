(ns gradle-licenses.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [gradle-licenses.core :as core]))

(def default-path "resources/gradle.json")

(def default-result-full-names
  [{:package "pl.droidsonroids.gif:android-gif-drawable:1.2.3", :license "The MIT License"}
   {:package "com.android.support:design:26.1.0", :license "The Apache Software License"}
   {:package "wsdl4j:wsdl4j:1.5.1", :license nil}
   {:package "org.checkerframework:checker-compat-qual:2.5.5", :license "GNU General Public License, version 2 (GPL2), with the classpath exception, The MIT License"}])

(def default-result-short-names
  [{:package "Android GIF Drawable Library:1.2.3", :license "The MIT License"}
   {:package "Design:26.1.0", :license "The Apache Software License"}
   {:package "WSDL4J:1.5.1", :license nil}
   {:package "Checker Qual:2.5.5", :license "GNU General Public License, version 2 (GPL2), with the classpath exception, The MIT License"}])

(def params-gradle-json->data-integration
  [[{} default-result-full-names "No options"]
   [{:skip-header true} default-result-full-names "Wrong options affect nothing"]
   [{:fully-qualified-names false} default-result-short-names "Turn off fully-qualified package names"]])

(deftest test-gradle-json->data-integration
  (testing "Test convertng gradle license plugin's JSON file into clojure map"
    (doseq [[options expected description] params-gradle-json->data-integration]
      (testing description
        (is (= expected (core/gradle-json->data default-path options)))))))

(def params-dependency->package
  [[{"project" "test" "version" "1.2.3" "dependency" "com.github.pilosus/test:1.2.3"}
    {}
    "com.github.pilosus/test:1.2.3"
    "Fully-qualified name implicit"]
   [{"project" "test" "version" "1.2.3" "dependency" "com.github.pilosus/test:1.2.3"}
    {:fully-qualified-names true}
    "com.github.pilosus/test:1.2.3"
    "Fully-qualified name explicit"]
   [{"project" "test" "version" "1.2.3" "dependency" "com.github.pilosus/test:1.2.3"}
    {:fully-qualified-names false}
    "test:1.2.3"
    "Short name explicit"]
   [{"project" nil "version" nil "dependency" "com.github.pilosus/test:1.2.3"}
    {:fully-qualified-names false}
    nil
    "Short name nil"]
   [{"project" "" "version" "" "dependency" "com.github.pilosus/test:1.2.3"}
    {:fully-qualified-names false}
    nil
    "Short name empty strings"]
   [{"project" "test" "version" nil "dependency" "com.github.pilosus/test:1.2.3"}
    {:fully-qualified-names false}
    "test"
    "Short name no version"]
   [{"project" "test" "version" "" "dependency" "com.github.pilosus/test:1.2.3"}
    {:fully-qualified-names false}
    "test"
    "Short name version is empty string"]
   [{"project" "" "version" "1.2.3" "dependency" "com.github.pilosus/test:1.2.3"}
    {:fully-qualified-names false}
    nil
    "Short name project is empty string"]])

(deftest test-dependency->package
  (testing "Test convertng gradle license plugin's JSON file into clojure map"
    (doseq [[dependency options expected description] params-dependency->package]
      (testing description
        (is (= expected (core/dependency->package dependency options)))))))
