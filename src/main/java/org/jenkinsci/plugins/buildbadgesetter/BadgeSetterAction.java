package org.jenkinsci.plugins.buildbadgesetter;

import hudson.model.BuildBadgeAction;

public class BadgeSetterAction implements BuildBadgeAction {
    private String text;

    public BadgeSetterAction(String text) {
        this.text = text;
    }

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public String getUrlName() {
        return null;
    }

    public String getText() {
        return text;
    }
}
