package de.lessvoid.nifty.elements;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import de.lessvoid.nifty.elements.tools.MethodResolver;

/**
 * A object and a method for the object.
 * @author void
 */
public class MethodInvoker {
  /**
   * logger.
   */
  private static Logger log = Logger.getLogger(MethodInvoker.class.getName());

  /**
   * object.
   */
  private Object[] target;

  /**
   * method.
   */
  private String methodWithName;

  /**
   * create null MethodInvoker.
   */
  public MethodInvoker() {
    this.methodWithName = null;
    this.target = null;
  }

  /**
   * Create new MethodInvoker.
   * @param methodParam method
   * @param targetParam target
   */
  public MethodInvoker(final String methodParam, final Object ... targetParam) {
    this.methodWithName = methodParam;
    this.target = targetParam;
  }

  /**
   * Set first.
   * @param object object
   */
  public void setFirst(final Object object) {
    if (methodWithName == null) {
      return;
    }
    if (target == null) {
      target = new Object[1];
    }
    this.target[0] = object;
  }

  /**
   * invoke method with optional parameters.
   * @param invokeParametersParam parameter array for call
   */
  public void invoke(final Object ... invokeParametersParam) {
    // nothing to do?
    if (target == null || target.length == 0 || methodWithName == null) {
      return;
    }

    // process all methods (first one wins)
    for (Object object : target) {
      if (object != null) {
        Method method = MethodResolver.findMethod(object.getClass(), methodWithName);
        if (method != null) {
          // we've found a method with the given name. now we need to match the parameters.
          //
          // 1) if the target method has parameters encoded we ignore the invokeParametersParam we've been
          //    called with and call the method as is.
          // 2) if the target method has no parameters there are two possibilities:
          //    2a) invokeParametersParam are given, in this case we'll try to forward them to the method
          //        if this is not possible we fall back to 2b)
          //    2b) just call the method without any parameters
          Object[] invokeParameters = MethodResolver.extractParameters(methodWithName);
          if (invokeParameters.length > 0) {
            // does the method supports the parameters?
            // TODO: not only check for the count but check the type too
            if (getMethodParameterCount(method) == invokeParameters.length) {
              log.info("invoking method '" + methodWithName + "' with (" + debugParaString(invokeParameters) + ")");
              callMethod(object, method, invokeParameters);
              return;
            } else {
              log.info("invoking method '" + methodWithName + "' (note: given invokeParameters have been ignored)");
              callMethod(object, method, new Object[0]);
              return;
            }
          } else {
            // no invokeParameters encoded. this means we can call the method as is or with the invokeParametersParam
            if (invokeParametersParam.length > 0) {
              if (getMethodParameterCount(method) == invokeParametersParam.length) {
                log.info("invoking method '" + methodWithName + "' with the actual parameters ("
                    + debugParaString(invokeParametersParam) + ")");
                callMethod(object, method, invokeParametersParam);
                return;
              } else {
                log.info("invoking method '" + methodWithName
                    + "' without parameters (invokeParametersParam mismatch)");
                callMethod(object, method, null);
              }
            } else {
              log.info("invoking method '" + methodWithName + "' without parameters");
              callMethod(object, method, null);
            }
          }
          return;
        }
      }
    }
  }

  /**
   * Invoke the given method on the given object.
   * @param targetObject target object to invoke method on
   * @param method method to invoke
   * @param invokeParameters parameters to use
   */
  private void callMethod(final Object targetObject, final Method method, final Object[] invokeParameters) {
    try {
      method.invoke(targetObject, invokeParameters);
    } catch (Exception e) {
      log.warning("error: " + e.getMessage());
    }
  }

  /**
   * return count of actual method parameters.
   * @param method method
   * @return count of parameters the method needs
   */
  private int getMethodParameterCount(final Method method) {
    Class < ? > [] parameterTypes = method.getParameterTypes();
    return parameterTypes.length;
  }

  /**
   * helper method to convert the given parameter object array into a string for debugging.
   * @param invokeParameters parameter array
   * @return string of parameter array in debug output friendly way
   */
  private String debugParaString(final Object[] invokeParameters) {
    StringBuffer paraStringBuffer = new StringBuffer();
    paraStringBuffer.append(invokeParameters[0].toString());
    for (int i = 1; i < invokeParameters.length; i++) {
      paraStringBuffer.append(", ");
      paraStringBuffer.append(invokeParameters[i].toString());
    }
    return paraStringBuffer.toString();
  }
}
