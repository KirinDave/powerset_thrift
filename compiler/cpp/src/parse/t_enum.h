#ifndef T_ENUM_H
#define T_ENUM_H

#include "t_enum_value.h"
#include <vector>

/**
 * An enumerated type. A list of constant objects with a name for the type.
 *
 * @author Mark Slee <mcslee@facebook.com>
 */
class t_enum : public t_type {
 public:
  t_enum(t_program* program) :
    t_type(program) {}

  void set_name(std::string name) {
    name_ = name;
  }
  
  void append(t_enum_value* constant) {
    constants_.push_back(constant);
  }

  const std::vector<t_enum_value*>& get_constants() {
    return constants_;
  }

  bool is_enum() const {
    return true;
  }

 private:
  std::vector<t_enum_value*> constants_;
};

#endif
