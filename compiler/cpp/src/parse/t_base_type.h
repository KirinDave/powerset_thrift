// Copyright (c) 2006- Facebook
// Distributed under the Thrift Software License
//
// See accompanying file LICENSE or visit the Thrift site at:
// http://developers.facebook.com/thrift/

#ifndef T_BASE_TYPE_H
#define T_BASE_TYPE_H

#include <cstdlib>
#include "t_type.h"

/**
 * A thrift base type, which must be one of the defined enumerated types inside
 * this definition.
 *
 * @author Mark Slee <mcslee@facebook.com>
 */
class t_base_type : public t_type {
 public:
  /**
   * Enumeration of thrift base types
   */
  enum t_base {
    TYPE_VOID,
    TYPE_STRING,
    TYPE_BOOL,
    TYPE_BYTE,
    TYPE_I16,
    TYPE_I32,
    TYPE_I64,
    TYPE_DOUBLE
  };

  t_base_type(std::string name, t_base base) :
    t_type(name),
    base_(base),
    string_list_(false),
    string_enum_(false) {}
    
  t_base get_base() const {
    return base_;
  }

  bool is_void() const {
    return base_ == TYPE_VOID;
  }

  bool is_string() const {
    return base_ == TYPE_STRING;
  }
  
  void set_string_list(bool val) {
    string_list_ = val;
  }

  bool is_string_list() const {
    return (base_ == TYPE_STRING) && string_list_;
  }

  void set_binary(bool val) {
    binary_ = val;
  }
  
  bool is_binary() const {
    return (base_ == TYPE_STRING) && binary_;
  }

  void set_string_enum(bool val) {
    string_enum_ = true;
  }

  bool is_string_enum() const {
    return base_ == TYPE_STRING && string_enum_;
  }

  void add_string_enum_val(std::string val) {
    string_enum_vals_.push_back(val);
  }

  const std::vector<std::string>& get_string_enum_vals() const {
    return string_enum_vals_;
  }

  bool is_base_type() const {
    return true;
  }

  virtual std::string get_fingerprint_material() const {
    std::string rv = t_base_name(base_);
    if (rv == "(unknown)") {
      throw "BUG: Can't get fingerprint material for this base type.";
    }
    return rv;
  }

  static std::string t_base_name(t_base tbase) {
    switch (tbase) {
      case TYPE_VOID   : return      "void"; break;
      case TYPE_STRING : return    "string"; break;
      case TYPE_BOOL   : return      "bool"; break;
      case TYPE_BYTE   : return      "byte"; break;
      case TYPE_I16    : return       "i16"; break;
      case TYPE_I32    : return       "i32"; break;
      case TYPE_I64    : return       "i64"; break;
      case TYPE_DOUBLE : return    "double"; break;
      default          : return "(unknown)"; break;
    }
  }

 private:
  t_base base_;

  bool string_list_;
  bool binary_;
  bool string_enum_;
  std::vector<std::string> string_enum_vals_;
};

#endif
