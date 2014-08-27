/**
 * 
 */
package com.mwlug.AD104;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lotus.domino.Database;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewNavigator;

import com.ibm.xsp.model.domino.DominoUtils;

/**
 * ALL REVIEWS
 * 
 * 
 * @author Mike McGarel (mmcgarel@czarnowski.com)
 * @author Devin S. Olson (dolson@czarnowski.com)
 * 
 */

public class Reviews implements Serializable {

	private static final long serialVersionUID = 1L;
	List<Review> _content;


	/* ZERO-ARGUMENT CONSTRUCTOR */
	public Reviews() {
	}


	public List<Review> getContent() {
		if ((null == this._content) || this._content.isEmpty()) {
			this.reload();
		}

		return this._content;
	}


	public void clear() {
		System.out.println("*");
		System.out.println("*");
		System.out.println("*");
		System.out.println("Reviews.clear()");

		if (null != this._content) {
			this._content.clear();
		}
	}


	public void reload() {
		Database db = null;
		View view = null;
		ViewNavigator nav = null;
		ViewEntry vent = null;
		ViewEntry stupidRecycleHack = null;

		try {
			System.out.println("*");
			System.out.println("*");
			System.out.println("*");
			System.out.println("Reviews.reload()");

			this.clear();

			final List<String> universalIDs = new ArrayList<String>();

			db = DominoUtils.getCurrentDatabase();
			view = db.getView("vwAllReviews");
			view.setAutoUpdate(false);
			nav = view.createViewNav();
			vent = nav.getFirstDocument();
			while (null != vent) {
				stupidRecycleHack = vent;
				universalIDs.add(vent.getUniversalID());
				vent = nav.getNextDocument();
				stupidRecycleHack.recycle();
			}

			if (!universalIDs.isEmpty()) {
				if (null == this._content) {
					this._content = new ArrayList<Review>();
				}

				for (final String universalID : universalIDs) {
					final Review review = new Review(db, universalID);
					this._content.add(review);
					System.out.println("\t Added" + review.toString());
				}
			}

		} catch (final Exception ex) {
			ex.printStackTrace();

		} finally {
			// recycle our domino objects or we will leak memory
			try {
				if (null != vent) {
					vent.recycle();
				}
				if (null != stupidRecycleHack) {
					stupidRecycleHack.recycle();
				}
				if (null != nav) {
					nav.recycle();
				}
				if (null != view) {
					view.recycle();
				}
				if (null != db) {
					db.recycle();
				}
			} catch (final Exception e) {
				System.out.println("EXCEPTION");
				System.out.print(this.toString());
				e.printStackTrace();
			}
		}
	}
}
