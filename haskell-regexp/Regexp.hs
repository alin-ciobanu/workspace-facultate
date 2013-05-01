module Regexp where

import Parser
import Data.String
import Data.Char (isLetter, isDigit)

matches expression [] = if (lastMatch == []) then [] else [(fst (head lastMatch))]
	where
		lastMatch = (match (fst (head (expr expression)))) []
matches expression input =
	if (currentMatch == []) then (matches expression (tail input))
	else
		if ((fst (head currentMatch)) == []) then
			(fst (head currentMatch)) : (matches expression (tail input))
		else
			(fst (head currentMatch)) : (matches expression (snd (head currentMatch)))
	where
		currentMatch = (match (fst (head (expr expression)))) input

data Regex = Letter Char 
			| Star Regex 
			| Alt Regex Regex 
			| Concat Regex Regex 
			| Question Regex 
			| Plus Regex
			deriving Show

match :: Regex -> Parser Char String
match (Letter l) = (token l) `transform` (:[])
match (Alt x y) = match x `alt` match y
match (Concat x y) =((match x) >*> (match y)) `transform` pairToString
match (Star x) = (maxList (match x)) `transform` concat
match (Question x) = (maxOptional (match x)) `transform` concat
match (Plus x) = (maxNeList (match x)) `transform` concat

-- transforma o pereche (String, String) in String (concateneaza string-urile din pereche)
pairToString :: (String, String) -> String
pairToString pair = fst pair ++ snd pair

expr = 	(
		maxList
		((atom >*>
		((maxOptional (((token '|') >*> atom) `transform` snd)))
		) `transform` (\(x, y) -> if ((length y) == 0) then x else (Alt x (head y))))
		)
		`transform` f
		where
			f ([x]) = x
			f (h:t) = Concat h (f t)
			f ([]) = Concat (Letter 'x') (Letter 'w')

atom = letter `alt`
		letterPlus `alt`
		letterStar `alt`
		letterQuestion `alt`
		(((token '(') >*> expr >*> (token ')')) `transform` f) `alt`
		(((token '(') >*> expr >*> (token ')') >*> (token '+')) `transform` fPlus) `alt`
		(((token '(') >*> expr >*> (token ')') >*> (token '*')) `transform` fStar) `alt`
		(((token '(') >*> expr >*> (token ')') >*> (token '?')) `transform` fQuestion)
	where
		f (_, (e, _)) = e
		fPlus(_, (e, (_, _))) = Plus e
		fStar(_, (e, (_, _))) = Star e
		fQuestion(_, (e, (_, _))) = Question e

letter = (spot isLetter) `transform` (\x -> Letter (x))
letterPlus = ((spot isLetter) >*> (spot isPlus)) `transform` (\(x, y) -> Plus (Letter x))
letterStar = ((spot isLetter) >*> (spot isStar)) `transform` (\(x, y) -> Star (Letter x))
letterQuestion = ((spot isLetter) >*> (spot isQuestion)) `transform` (\(x, y) -> Question (Letter x))


isStar :: Char -> Bool
isStar ch = if (ch == '*') then True else False

isQuestion :: Char -> Bool
isQuestion ch = if (ch == '?') then True else False

isPlus :: Char -> Bool
isPlus ch = if (ch == '+') then True else False

isAlt :: Char -> Bool
isAlt ch = if ch == '|' then True else False
