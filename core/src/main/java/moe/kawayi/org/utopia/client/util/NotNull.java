//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The NotNull.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.client.util;

import java.lang.annotation.*;

/**
 * 标记一个值不为空
 */
@Target({
        ElementType.LOCAL_VARIABLE,
        ElementType.PARAMETER,
        ElementType.FIELD,
        ElementType.METHOD
})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface NotNull {/*Dot nothing*/}