(ns arabic-fontified-ios.core
  (:require
   [reagent.core :as r]
   [clojure.string :as str]
   [cljsjs.clipboard]))

(defonce magic-char "﮼")

(defn str-insert
  "Insert c in string s at index i."
  [s c i]
  (str (subs s 0 i) c (subs s i)))

(defn strs-insert
  [char idxs string]
  (loop [char char
         idxs idxs
         string string]
    (if (empty? idxs)
      string
      (recur char
             (rest idxs)
             (str-insert string char (first idxs))))))

(defn indices [string val]
  (loop [string string
         val val
         start 0
         acc []]
    (if (nil? (str/index-of string val start))
      acc
      (recur string
             val
             (inc (str/index-of string val start))
             (conj acc (str/index-of string val start))))))

(defn convert [txt]
  (let [split-txt (str/split txt #" ")
        tmp (->> split-txt
                 (map (fn [c] (str magic-char c)))
                 (interpose " ")
                 (apply str))
        new-lines (map inc (indices tmp "\n"))]
    (strs-insert magic-char new-lines tmp)))

(convert "hello\nthere\nin\nthis\napplication")

(defn clipboard-button [label target show]
  (let [clipboard-atom (r/atom nil)]
    (r/create-class
     {:display-name "clipboard-button"
      :component-did-mount
      #(let [clipboard (new js/Clipboard (r/dom-node %))]
         (reset! clipboard-atom clipboard))
      :component-will-unmount
      #(when-not (nil? @clipboard-atom)
         (.destroy @clipboard-atom)
         (reset! clipboard-atom nil))
      :reagent-render
      (fn [label target show]
        [:button.btn.btn-primary.btn-lg
         {:data-clipboard-target target
          :disabled (if show true false)}
         label])})))

(defn home-page []
  (let [text-in (r/atom "")
        text-out (r/atom "")]
    (fn []
      [:div.container
       [:div.columns
        [:div.column.col-12
         [:div
          [:div.input-group
           [:textarea.form-input {:on-change #(do
                                                (reset! text-in (-> % .-target .-value))
                                                (when (empty? @text-in) (reset! text-out "")))
                                  :placeholder "أدخل النص هنا"
                                  :rows "4"
                                  :value @text-in
                                  :style {:text-align "center"}}]]
          [:div.columns
           [:div.column.col-6
            [:div.card
             [:button.btn.btn-primary.btn-lg {:on-click #(reset! text-out (convert @text-in))
                                              :disabled (if (empty? @text-in) true false)} "حوّل"]]]

           [:div.column.col-6
            [:div.card
             [:button.btn.btn-primary.btn-lg {:on-click #(reset! text-in "")
                                              :disabled (if (empty? @text-in) true false)} "امسح"]]]]]]

        [:div.column.col-12
         [:div.card
          [:pre.text-center {:id "copy-cell"} @text-out]
          [clipboard-button "انسخ" "#copy-cell" (empty? @text-out)]]]

        [:div.column.col-12.text-center
         [:div [:sub.text-gray "برمجة: ناصر الشمري"]]
         [:a.btn.btn-link {:href "https://twitter.com/nashamri"} "Twitter"]
         [:img.avatar.avatar-xm {:src "./img/avatar.jpg" :width "34px"}]
         [:a.btn.btn-link {:href "https://github.com/nashamri/arabic-fontified-ios"} "GitHub"]]

        ]])))


(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
