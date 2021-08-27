(ns gradle-licenses.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [gradle-licenses.core :as core]))

(def default-path "resources/gradle.json")

(def default-result
  [{:package "pl.droidsonroids.gif:android-gif-drawable:1.2.3", :license "The MIT License"}
   {:package "com.android.support:design:26.1.0", :license "The Apache Software License"}
   {:package "wsdl4j:wsdl4j:1.5.1", :license nil}
   {:package "org.checkerframework:checker-compat-qual:2.5.5", :license "GNU General Public License, version 2 (GPL2), with the classpath exception, The MIT License"}])

(def params-gradle-json->data-integration
  [[{} default-result "No options"]
   [{:skip-header true} default-result "Options affect nothing"]])

(deftest test-gradle-json->data-integration
  (testing "Test convertng gradle license plugin's JSON file into clojure map"
    (doseq [[options expected description] params-gradle-json->data-integration]
      (testing description
        (is (= expected (core/gradle-json->data default-path options)))))))
