(ns einrid
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]
        [clojure.tools.macro :as macro]))


(defne righto [x y l]
  ([_ _ [x y . r]])
  ([_ _ [_ . r]] (righto x y r)))

(defn nexto [x y l]
  (conde
    [(righto x y l)]
    [(righto y x l)]))

(comment
  "Hints:

The Brit lives in the red house.
The Swede keeps dogs as pets.
The Dane drinks tea.
The green house is on the left of the white house.
The green homeowner drinks coffee.
The person who smokes Pall Mall rears birds.
The owner of the yellow house smokes Dunhill.
The man living in the center house drinks milk.
The Norwegian lives in the first house.
The man who smokes Blend lives next to the one who keeps cats.
The man who keeps the horse lives next to the man who smokes Dunhill.
The owner who smokes Bluemaster drinks beer.
The German smokes prince.
The Norwegian lives next to the blue house.
The man who smokes Blend has a neighbor who drinks water.")

(defn fisho [hs]
  (macro/symbol-macrolet
   [_ (lvar)]
   (all
    (== (list _ _ (list _ _ 'milk _ _) _ _) hs)
    (firsto hs (list 'norwegian _ _ _ _))
    (nexto (list 'norwegian _ _ _ _) (list _ _ _ _ 'blue) hs)
    (righto (list _ _ _ _ 'white) (list _ _ _ _ 'green) hs)
    (membero (list 'brit _ _ _ 'red) hs)
    (membero (list 'swede _ _ 'dog _) hs)
    (membero (list 'dane _ 'tea _ _) hs)
    (membero (list _ _ 'coffee _ 'green) hs)
    (membero (list _ 'pallmall _ 'bird _) hs)
    (membero (list _ 'dunhill _ _ 'yellow) hs)
    (membero (list _ 'bluemaster 'beer _ _) hs)
    (membero (list 'german 'prince _ _ _) hs)

    (nexto (list _ 'blend _ _ _) (list _ _ _ 'cat _) hs)
    (nexto (list _ _ _ 'horse _) (list _ 'dunhill _ _ _) hs)
    (nexto (list _ 'blend _ _ _) (list _ _ 'water _ _) hs))))

(defn ^:export find-fish []
  (doall (run-nc 1 [q] (fisho q))))

(defn print-fish []
  (println (pr-str (run 1 [q] (fisho q)))))

(find-fish)

(comment
  (((norwegian dunhill water cat yellow)
    (dane blend tea horse blue)
    (brit pallmall milk bird red)
    (swede bluemaster beer dog white)
    (german prince coffee _0 green))))
