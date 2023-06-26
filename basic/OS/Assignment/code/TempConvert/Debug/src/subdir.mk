################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/converters.cpp \
../src/helpers.cpp \
../src/tempConvert.cpp 

OBJS += \
./src/converters.o \
./src/helpers.o \
./src/tempConvert.o 

CPP_DEPS += \
./src/converters.d \
./src/helpers.d \
./src/tempConvert.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


