package org.jenkinsci.plugins.buildbadgesetter;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;
import org.jenkinsci.plugins.tokenmacro.MacroEvaluationException;
import org.jenkinsci.plugins.tokenmacro.TokenMacro;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

public class BadgeSetter extends BuildWrapper {

    public final String template;

    @DataBoundConstructor
    public BadgeSetter(String template) {
        this.template = template;
    }

    @Override
    public Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener)
            throws IOException, InterruptedException {
        setBadge(build, listener);

        return new Environment() {
            @Override
            public boolean tearDown(AbstractBuild build, BuildListener listener)
                    throws IOException, InterruptedException {
                return true;
            }
        };
    }

    private void setBadge(AbstractBuild build, BuildListener listener) throws IOException, InterruptedException {
        try {
            build.addAction(new BadgeSetterAction(TokenMacro.expandAll(build, listener, template)));
        } catch (MacroEvaluationException e) {
            listener.getLogger().println(e.getMessage());
        }
    }

    @Extension
    public static class DescriptorImpl extends BuildWrapperDescriptor {
        @Override
        public boolean isApplicable(AbstractProject<?, ?> item) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Set Badge";
        }
    }
}