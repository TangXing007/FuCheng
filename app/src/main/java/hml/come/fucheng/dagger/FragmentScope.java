package hml.come.fucheng.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by tangdi on 8/15/17.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface FragmentScope {
}
