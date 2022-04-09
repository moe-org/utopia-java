//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The IllegalCancellationException.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2022 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.event;

/**
 * 如果对{@link Event#canCancel()}返回false的Event调用{@link Event#setCancel(boolean)}则应该抛出此异常。
 */
public class IllegalCancellationException extends Exception{
    /**
     * 构造一个IllegalCancellationException
     */
    public IllegalCancellationException(){
        super();
    }
}
