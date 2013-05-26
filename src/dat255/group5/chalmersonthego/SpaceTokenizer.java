package dat255.group5.chalmersonthego;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.MultiAutoCompleteTextView.Tokenizer;

/**
 * Used inside MultiAutoCompletetextview to add end sign. Basically this class
 * replace a comma(standard) with a space. Source of code:
 * http://stackoverflow.com/a/4596652/2069530
 * 
 * @author Anders Nordin
 */
public class SpaceTokenizer implements Tokenizer {

	/**
	 * Find where the tooken starts
	 */
	public int findTokenStart(CharSequence text, int cursor) {
		int i = cursor;

		while (i > 0 && text.charAt(i - 1) != ' ') {
			i--;
		}
		while (i < cursor && text.charAt(i) == ' ') {
			i++;
		}
		return i;
	}

	/**
	 * Find where tooken ends
	 */
	public int findTokenEnd(CharSequence text, int cursor) {
		int i = cursor;
		int len = text.length();

		while (i < len) {
			if (text.charAt(i) == ' ') {
				return i;
			} else {
				i++;
			}
		}
		return len;
	}

	/**
	 * Add the terminate tooken
	 */
	public CharSequence terminateToken(CharSequence text) {
		int i = text.length();

		while (i > 0 && text.charAt(i - 1) == ' ') {
			i--;
		}

		if (i > 0 && text.charAt(i - 1) == ' ') {
			return text;
		} else {
			if (text instanceof Spanned) {
				SpannableString sp = new SpannableString(text + " ");
				TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
						Object.class, sp, 0);
				return sp;
			} else {
				return text + " ";
			}
		}
	}

	/**
	 * As the function puts a space afterward we can to remove it when searching
	 * in the database. Otherwise it'll cause error
	 * 
	 * @param s
	 *            , string to be trimmed
	 * @return string without space at the end
	 */
	public static String removeLastCharIfSpace(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}
		char t = s.charAt(s.length() - 1);

		if (t == ' ')
			return s.substring(0, s.length() - 1);
		else
			return s;
	}
}