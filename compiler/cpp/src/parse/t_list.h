// Copyright (c) 2006- Facebook
// Distributed under the Thrift Software License
//
// See accompanying file LICENSE or visit the Thrift site at:
// http://developers.facebook.com/thrift/

#ifndef T_LIST_H
#define T_LIST_H

#include "t_container.h"

/**
 * A list is a lightweight container type that just wraps another data type.
 *
 * @author Mark Slee <mcslee@facebook.com>
 */
class t_list : public t_container {
 public:
  t_list(t_type* elem_type) :
    elem_type_(elem_type) {}

  t_type* get_elem_type() const {
    return elem_type_;
  }
  
  bool is_list() const {
    return true;
  }

 private:
  t_type* elem_type_;
};

#endif

