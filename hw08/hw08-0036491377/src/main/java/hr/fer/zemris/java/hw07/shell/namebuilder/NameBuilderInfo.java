package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.regex.Matcher;

public interface NameBuilderInfo {
    StringBuilder getStringBuilder();
    String getGroup(int index);
    int getGroupCount();
}
