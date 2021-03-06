/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.management.internal.configuration.functions;

import org.apache.logging.log4j.Logger;

import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.internal.cache.InternalCache;
import org.apache.geode.internal.cache.execute.InternalFunction;
import org.apache.geode.logging.internal.log4j.api.LogService;
import org.apache.geode.management.internal.cli.functions.CliFunctionResult;

public class RecreateCacheFunction implements InternalFunction {
  private static final Logger logger = LogService.getLogger();

  @Override
  public void execute(FunctionContext context) {
    CliFunctionResult result = null;
    InternalCache cache = (InternalCache) context.getCache();
    try {
      cache.reLoadClusterConfiguration();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      result = new CliFunctionResult(context.getMemberName(), false, e.getMessage());
      context.getResultSender().lastResult(result);
      return;
    }
    result = new CliFunctionResult(context.getMemberName(), true, "Cache successfully reloaded.");
    context.getResultSender().lastResult(result);
  }

  @Override
  public String getId() {
    return RecreateCacheFunction.class.getName();
  }

}
