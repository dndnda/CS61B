/**
 * A class for palindrome operations.
 */
public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for(int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return true;
        }

        return isPalindromeHelper(word.toCharArray(), 0, word.length() - 1, new CharacterComparator() {
            @Override
            public boolean equalChars(char x, char y) {
                return x == y;
            }
        });
    }

//    private boolean isPalindromeHelper(char[] word, int front, int last) {
//        if (front >= last) {
//            return true;
//        }
//        if (word[front] != word[last]) {
//            return false;
//        }
//        return isPalindromeHelper(word, front + 1, last - 1);
//    }


    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return true;
        }
        return isPalindromeHelper(word.toCharArray(), 0, word.length() - 1, cc);
    }

    private boolean isPalindromeHelper(char[] word, int front, int last, CharacterComparator cc) {
        if(front >= last) {
            return true;
        }
        if (!cc.equalChars(word[front], word[last])) {
            return false;
        }
        return isPalindromeHelper(word, front + 1, last - 1, cc);
    }

}
