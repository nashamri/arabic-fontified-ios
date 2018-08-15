(ns ^:figwheel-no-load fontify-twitter.dev
  (:require
    [fontify-twitter.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
