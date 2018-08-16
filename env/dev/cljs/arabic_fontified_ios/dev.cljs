(ns ^:figwheel-no-load arabic-fontified-ios.dev
  (:require
    [arabic-fontified-ios.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
