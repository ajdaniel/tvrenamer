package com.google.code.tvrenamer.view;

import java.text.NumberFormat;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.gjt.sp.util.ProgressObserver;

public class FileCopyMonitor implements ProgressObserver {
	private long maximum;
	private final Label label;
	private int loopCount = 0;
	private final NumberFormat format = NumberFormat.getPercentInstance();

	public FileCopyMonitor(Label label, long maximum) {
		this.label = label;
		setMaximum(maximum);
		setValue(0);
		format.setMaximumFractionDigits(1);
	}

	public void setValue(final long value) {
		if (loopCount++ % 500 == 0) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (label.isDisposed()) {
						return;
					}
					label.setText(format.format((double) value / maximum));
				}
			});
		}
	}

	public void setMaximum(long value) {
		maximum = value;
	}

	public void setStatus(final String status) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (label.isDisposed()) {
					return;
				}
				label.setToolTipText(status);
			}
		});
	}
}
