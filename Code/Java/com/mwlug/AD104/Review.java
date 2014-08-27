/**
 * 
 */
package com.mwlug.AD104;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import lotus.domino.Database;
import lotus.domino.Document;

import com.ibm.xsp.model.domino.DominoUtils;

/**
 * REVIEW
 * 
 * 
 * @author Mike McGarel (mmcgarel@czarnowski.com)
 * @author Devin S. Olson (dolson@czarnowski.com)
 * 
 */
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;
	private String _universalID;
	private String _reviewer;
	private String _productName;
	private String _comments;


	/* ZERO-ARGUMENT CONSTRUCTOR */
	public Review() {
		// do nothing
	}


	public Review(final Database database, final String unid) {
		this.load(database, unid);
	}


	@Override
	public String toString() {
		try {
			final StringBuilder sb = new StringBuilder(this.getClass().getName());
			sb.append("|");
			sb.append("UniversalID:");
			sb.append(this.getUniversalID());
			sb.append("ProductName:");
			sb.append(this.getProductName());
			sb.append("Reviewer:");
			sb.append(this.getReviewer());
			// sb.append("Comments:");
			// sb.append(this.getComments());

			return sb.toString();

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return "";
	}


	public void load(final String unid) {

		Database database = null;

		try {
			if ((null == unid) || "".equals(unid.trim())) {
				// do nothing

			} else {
				database = DominoUtils.getCurrentDatabase();
				this.load(database, unid);
			}

		} catch (final Exception ex) {
			ex.printStackTrace();

		} finally {
			// recycle our domino objects or we will leak memory
			try {
				if (null != database) {
					database.recycle();
				}
			} catch (final Exception e) {
				System.out.println("EXCEPTION");
				System.out.print(this.toString());
				e.printStackTrace();
			}
		}
	}


	public void load(final Database database, final String unid) {

		Document doc = null;
		System.out.println("*");
		System.out.println("*");
		System.out.println("Review.load()");
		System.out.println("\t unid: " + unid);

		try {
			if ((null == unid) || "".equals(unid.trim())) {
				// do nothing

			} else {
				doc = database.getDocumentByUNID(unid);
				this._universalID = unid;

				this._reviewer = doc.getItemValueString("Reviewer");
				this._productName = doc.getItemValueString("ProductName");
				this._comments = doc.getItemValueString("Comments");
			}

			System.out.println("Review");
			System.out.println(this.toString());

		} catch (final Exception ex) {
			ex.printStackTrace();

		} finally {
			// recycle our domino objects or we will leak memory
			try {
				if (null != doc) {
					doc.recycle();
				}
			} catch (final Exception e) {
				System.out.println("EXCEPTION");
				System.out.print(this.toString());
				e.printStackTrace();
			}
		}
	}


	// Type-Safety for the put method
	@SuppressWarnings("unchecked")
	public void save() {

		Database db = null;
		Document doc = null;

		try {
			System.out.println("*");
			System.out.println("*");
			System.out.println("*");
			System.out.println("*");
			System.out.println("*");
			System.out.println("Review.save()");

			db = DominoUtils.getCurrentDatabase();
			final String unid = this.getUniversalID();
			if ((null == unid) || "".equals(unid.trim())) {
				// NEW DOCUMENT
				doc = db.createDocument();
				doc.replaceItemValue("Form", "fmReview");

			} else {
				// EXISTING DOCUMENT
				doc = db.getDocumentByUNID(unid);
			}

			System.out.println("\t UniversalID: " + this.getUniversalID());
			System.out.println("\t Reviewer: " + this.getReviewer());
			System.out.println("\t Product: " + this.getProductName());
			System.out.println("\t Comments: " + this.getComments());

			doc.replaceItemValue("Reviewer", this.getReviewer());
			doc.replaceItemValue("ProductName", this.getProductName());
			doc.replaceItemValue("Comments", this.getComments());

			if (doc.save(true)) {
				System.out.println("DOCUMENT SAVED");
				final FacesContext fc = FacesContext.getCurrentInstance();
				Reviews reviews = (Reviews) fc.getExternalContext().getApplicationMap().get("Reviews");
				if (null == reviews) {
					reviews = new Reviews();
					fc.getExternalContext().getApplicationMap().put("Reviews", reviews);
				}

				reviews.reload();

			} else {
				System.out.println("******");
				System.out.println("DOCUMENT NOT SAVED");
				System.out.println("******");
			}

		} catch (final Exception ex) {

			ex.printStackTrace();

		} finally {

			// recycle our domino objects or we will leak memory
			try {
				if (null != doc) {
					doc.recycle();
				}
				if (null != db) {
					db.recycle();
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * @return the UniversalID
	 */
	public String getUniversalID() {
		if (null == this._universalID) {
			this._universalID = "";
		}

		return this._universalID;
	}


	/**
	 * @param UniversalID
	 *            the UniversalID to set
	 */
	public void setUniversalID(final String universalID) {
		this._universalID = universalID;
	}


	/**
	 * @return the reviewer
	 */
	public String getReviewer() {
		if (null == this._reviewer) {
			this._reviewer = "";
		}

		return this._reviewer;
	}


	/**
	 * @param reviewer
	 *            the reviewer to set
	 */
	public void setReviewer(final String reviewer) {
		this._reviewer = reviewer;
	}


	/**
	 * @return the product
	 */
	public String getProductName() {
		if (null == this._productName) {
			this._productName = "";
		}

		return this._productName;
	}


	/**
	 * @param product
	 *            the product to set
	 */
	public void setProductName(final String productName) {
		this._productName = productName;
	}


	/**
	 * @return the comments
	 */
	public String getComments() {
		if (null == this._comments) {
			this._comments = "";
		}

		return this._comments;
	}


	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(final String comments) {
		System.out.println("*");
		System.out.println("*");
		System.out.println("Review.setComments()");
		System.out.println("\t Comments: " + comments);
		this._comments = comments;
	}
}
