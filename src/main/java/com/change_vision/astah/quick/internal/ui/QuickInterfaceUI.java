package com.change_vision.astah.quick.internal.ui;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.astah.quick.internal.command.Commands;
import com.change_vision.astah.quick.internal.model.QuickProperties;
import com.change_vision.astah.quick.internal.ui.installed.InstalledWizardDialog;

public class QuickInterfaceUI {

	@SuppressWarnings("serial")
	private final class ShowQuickCommandWindowAction extends AbstractAction {
		private Commands commands;

		public ShowQuickCommandWindowAction(Commands commands) {
			super("ShowQuickCommandWindow");
			this.commands = commands;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
            if(window == null){
                createQuickWindow();
            }
            if(window.isVisible()){
                window.close();
                window.reset();
            }else{
                window.open();
            }
		}

		private void createQuickWindow() {
			AstahAPIWrapper wrapper = new AstahAPIWrapper();
			JFrame frame = wrapper.getMainFrame();
			window = new QuickWindow(frame, commands);
			setClickOutsideWindowBehavior();
		}

		private void setClickOutsideWindowBehavior() {
			Toolkit.getDefaultToolkit().addAWTEventListener(
					new ClickOutsideWindowListener(), AWTEvent.MOUSE_EVENT_MASK);
		}
	}

	private final class ClickOutsideWindowListener implements AWTEventListener {
		@Override
		public void eventDispatched(AWTEvent event) {
			int id = event.getID();
			if (id == MouseEvent.MOUSE_CLICKED) {
				if (event instanceof MouseEvent) {
					MouseEvent me = (MouseEvent) event;
					Object source = me.getSource();
					if (source instanceof Component) {
						Component c = (Component) source;
						if (window != null
								&& window.isVisible()
								&& (window.isAncestorOf(c) || c == window) == false) {
							window.close();
							window.reset();
						}
					}
				}
			}
		}
	}

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(QuickInterfaceUI.class);

	private KeyboardFocusManager focusManager;
	private final OpenQuickWindowEventDispatcher dispatcher;
	private final QuickProperties properties = QuickProperties.getInstance();
	private QuickWindow window;
	private Commands commands;
	private KeyStroke installedKeyStroke;

	public QuickInterfaceUI(Commands commands) {
		this.commands = commands;
		this.dispatcher = new OpenQuickWindowEventDispatcher(properties,
				commands);
		this.focusManager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
	}

	public void install() {
		logger.trace("install quick window ui");
		if (properties.exists() == false) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					AstahAPIWrapper wrapper = new AstahAPIWrapper();
					final JFrame parent = wrapper.getMainFrame();

					InstalledWizardDialog dialog = new InstalledWizardDialog(
							parent);
					dialog.setVisible(true);
					dialog.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							properties.store();
							JComponent target = getTargetComponent();
							if (target == null) {
								return;
							}
							doInstall(target);

						}
					});
				}
			});
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					JComponent target = getTargetComponent();
					if (target == null) {
						return;
					}
					doInstall(target);
				}
			});
		}
	}

	public void uninstall() {
		logger.trace("uninstall quick window ui");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JComponent target = getTargetComponent();
				if (target == null) {
					return;
				}
				target.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).remove(installedKeyStroke);
			}
		});
	}

	private JComponent getTargetComponent() {
		AstahAPIWrapper wrapper = new AstahAPIWrapper();
		final JFrame parent = wrapper.getMainFrame();
		Container rootPane = parent.getContentPane();
		if (rootPane instanceof JComponent) {
			return (JComponent) rootPane;
		}
		return null;
	}

	private KeyStroke getStroke() {
		String stroke = properties.getKeyStroke();
		KeyStroke strokeEvent = KeyStroke.getKeyStroke(stroke);
		return strokeEvent;
	}

	private void doInstall(JComponent target) {
		KeyStroke stroke = getStroke();
		target.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stroke, "ShowQuickCommandWindow");
		target.getActionMap().put("ShowQuickCommandWindow", new ShowQuickCommandWindowAction(commands));
		installedKeyStroke = stroke;
	}

}
