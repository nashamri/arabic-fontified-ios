(ns fontify-twitter.prod
  (:require
    [fontify-twitter.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
