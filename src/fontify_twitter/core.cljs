(ns fontify-twitter.core
  (:require
   [reagent.core :as r]
   [clojure.string :as str]
   [cljsjs.clipboard]))

;; "﮼ "

(defn convert [txt]
  (let [st (str/split txt #" ")
        pt (map (fn [c] (str "﮼" c)) st)
        it (interpose " " pt)]
    (apply str it)))

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
      [:div.columns
       [:div.column.col-12
        [:div.empty
         [:div.input-group
          [:input.form-input.input-lg {:on-change #(reset! text-in (-> % .-target .-value))
                                       :placeholder "أدخل النص هنا"
                                       :style {:text-align "center"}}]
          [:button.btn.btn-primary.btn-lg {:on-click #(reset! text-out (convert @text-in))
                                           :disabled (if (empty? @text-in) true false)} "حوّل"]]]]

       [:div.column.col-12
        [:div.divider]]

        [:div.column.col-12
         [:div.card
          [:h1.text-center {:id "copy-cell"} @text-out]
          [clipboard-button "انسخ" "#copy-cell" (empty? @text-out)]]]

       [:div.column.col-12
        [:div.divider]]

       [:div.column.col-12.text-center
        [:div [:sub.text-gray "Made by: Nasser Alshammari"]]
        [:a.btn.btn-link {:href "https://twitter.com/nashamri"} "Twitter"]
        [:img.avatar.avatar-xm {:src "./img/avatar.jpg" :width "34px"}]
        [:a.btn.btn-link {:href "https://github.com/nashamri/fontify-twitter"} "GitHub"]]

       ])))


(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))

(not (empty? " "))
