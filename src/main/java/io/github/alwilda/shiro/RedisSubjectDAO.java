package io.github.alwilda.shiro;

import io.github.alwilda.shiro.session.RedisSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;

@Slf4j
public class RedisSubjectDAO extends DefaultSubjectDAO {

    /**
     * {@inheritDoc}
     * <p>
     * 起初为了移除 {@link DefaultSubjectContext#AUTHENTICATED_SESSION_KEY}、{@link DefaultSubjectContext#PRINCIPALS_SESSION_KEY}，后来直接在 RedisSession 里禁止保存。
     *
     * @param subject the subject for which state will be persisted to its session.
     * @see RedisSession#setAttribute(Object, Object)
     * @see #removeFromSession(Subject)
     */
    @Override
    protected void saveToSession(Subject subject) {
        super.saveToSession(subject);
    }
}
