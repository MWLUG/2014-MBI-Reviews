/**
 * 
 */
package com.mwlug.AD104;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * SAMPLE CATALOG
 * 
 * 
 * @author Devin S. Olson (dolson@czarnowski.com)
 * @author Mike McGarel (mmcgarel@czarnowski.com)
 * 
 */
public class Catalog implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<SelectItem> _selectItems;


	/* ZERO-ARGUMENT CONSTRUCTOR */
	public Catalog() {
		// do nothing
	}


	public List<SelectItem> getSelectItems() {
		if (null == this._selectItems) {
			this._selectItems = this.buildSelectItems();
		}

		// return this._selectItems;
		System.out.println("*");
		System.out.println("*");
		System.out.println("*");
		System.out.println("*");
		System.out.println("*");

		final List<SelectItem> result = this.buildSelectItems();
		if (null == result) {
			System.out.println("Catalog.getSelectItems(): null");
		} else {
			System.out.println("Catalog.getSelectItems():");
			for (final SelectItem selectitem : result) {
				System.out.println("\t " + selectitem.getLabel() + "\t | " + selectitem.getValue());
			}
		}

		return result;

	}


	public List<String> getNames() {
		final List<String> result = new ArrayList<String>();

		result.add("Hill Conqueror");
		result.add("Mud Buster");
		result.add("Trail Blazer");

		return result;
	}


	private List<SelectItem> buildSelectItems() {
		try {
			final List<SelectItem> result = new ArrayList<SelectItem>();

			/* First (default) option */
			SelectItem option = new SelectItem();
			option.setLabel("-- Select a product --");
			option.setValue("");
			result.add(option);

			/* Loop through all the Bikes */
			for (final String name : this.getNames()) {
				option = new SelectItem();
				option.setLabel(name);
				option.setValue(name);
				result.add(option);
			}

			return result;

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
