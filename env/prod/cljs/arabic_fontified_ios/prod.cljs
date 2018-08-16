(ns arabic-fontified-ios.prod
  (:require
    [arabic-fontified-ios.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
