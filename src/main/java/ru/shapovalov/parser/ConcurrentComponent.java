package ru.shapovalov.parser;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;

public abstract class ConcurrentComponent extends CustomComponent {
    @Override
    public synchronized void setCompositionRoot(Component parent) {
        super.setCompositionRoot(parent);
    }

    @Override
    public synchronized Component getCompositionRoot() {
        return super.getCompositionRoot();
    }

    protected void executeUpdate(Runnable update) {
        UI ui = null;
        synchronized (this) {
            ui = getUI();
            if (ui == null)
                update.run();
        }
        if (ui != null) {
            synchronized (ui) {
                update.run();
            }
        }
    }

}
