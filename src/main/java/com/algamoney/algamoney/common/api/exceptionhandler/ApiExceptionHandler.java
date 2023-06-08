//package com.algamoney.algamoney.common.api.exceptionhandler;
//
//import java.time.OffsetDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import javax.validation.ConstraintViolationException;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.springframework.beans.TypeMismatchException;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.validation.BindException;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//import org.springframework.web.servlet.NoHandlerFoundException;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import com.algamoney.algamoney.common.api.model.Problem;
//import com.algamoney.algamoney.common.api.model.ProblemObject;
//import com.algamoney.algamoney.common.api.model.ProblemType;
//import com.algamoney.algamoney.common.domain.BusinessException;
//import com.algamoney.algamoney.common.domain.EntityInUseException;
//import com.algamoney.algamoney.common.domain.EntityNotFoundException;
//import com.fasterxml.jackson.databind.JsonMappingException.Reference;
//import com.fasterxml.jackson.databind.exc.InvalidFormatException;
//import com.fasterxml.jackson.databind.exc.PropertyBindingException;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestControllerAdvice
//public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
//
//	private final MessageSource messageSource;
//
//	private static final String END_USER_GENERIC_ERROR_MESSAGE = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";
//
//	public ApiExceptionHandler(MessageSource messageSource) {
//		this.messageSource = messageSource;
//	}
//
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
//		var problemType = ProblemType.SYSTEM_ERROR;
//		var status = problemType.getStatus();
//		var detail = END_USER_GENERIC_ERROR_MESSAGE;
//
//		log.error(ex.getMessage(), ex);
//
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
//
//		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
//	}
//
//	@ExceptionHandler(AccessDeniedException.class)
//	public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
//		var problemType = ProblemType.FORBIDDEN;
//		var status = problemType.getStatus();
//		var detail = ex.getMessage();
//
//		var problem = createProblemBuilder(status, problemType, detail)
//				.userMessage("Você não possui permissão para executar essa operação").build();
//
//		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
//	}
//
//	@ExceptionHandler(EntityNotFoundException.class)
//	public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
//		return handleNotFound(ex, request);
//	}
//
//	@ExceptionHandler(EntityInUseException.class)
//	public ResponseEntity<Object> handleEntityInUse(EntityInUseException ex, WebRequest request) {
//		var problemType = ProblemType.ENTITY_IN_USE;
//		var status = problemType.getStatus();
//		var detail = ex.getMessage();
//
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
//
//		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
//	}
//
//	@ExceptionHandler(BusinessException.class)
//	public ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request) {
//		var problemType = ProblemType.BUSINESS_ERROR;
//		var status = problemType.getStatus();
//		var detail = ex.getMessage();
//
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
//
//		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
//	}
//
//
//	@Override
//	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
//			WebRequest request) {
//
//		return handleValidationException(ex, headers, status, request, ex.getBindingResult());
//	}
//
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//		return handleValidationException(ex, headers, status, request, ex.getBindingResult());
//	}
//
//	@Override
//	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//
//		if (ex instanceof MissingServletRequestParameterException) {
//			return handleMissing((MissingServletRequestParameterException) ex, headers, request);
//		}
//
//		if (ex instanceof ConstraintViolationException) {
//			return handleConstraint((ConstraintViolationException) ex, headers, request);
//		}
//
//		if (body == null) {
//			body = Problem.builder().timestamp(OffsetDateTime.now()).title(status.getReasonPhrase())
//					.status(status.value()).userMessage(END_USER_GENERIC_ERROR_MESSAGE).build();
//		} else if (body instanceof String) {
//			body = Problem.builder().timestamp(OffsetDateTime.now()).title(String.valueOf(body)).status(status.value())
//					.userMessage(END_USER_GENERIC_ERROR_MESSAGE).build();
//		}
//
//		return super.handleExceptionInternal(ex, body, headers, status, request);
//	}
//
//	@Override
//	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//
//		var problemType = ProblemType.RESOURCE_NOT_FOUND;
//		var detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(END_USER_GENERIC_ERROR_MESSAGE)
//				.build();
//
//		return handleExceptionInternal(ex, problem, headers, status, request);
//	}
//
//	@Override
//	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//
//		if (ex instanceof MethodArgumentTypeMismatchException) {
//			var mismatchException = (MethodArgumentTypeMismatchException) ex;
//
//			if (isRequiredPathParameterUUID(mismatchException)) {
//				return handleGenericNotFound(mismatchException, headers, status, request);
//			}
//
//			return handleMethodArgumentTypeMismatch(mismatchException, headers, status, request);
//		}
//
//		return super.handleTypeMismatch(ex, headers, status, request);
//	}
//
//	@Override
//	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//		var rootCause = ExceptionUtils.getRootCause(ex);
//
//		if (rootCause instanceof InvalidFormatException) {
//			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
//		} else if (rootCause instanceof PropertyBindingException) {
//			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
//		}
//
//		var problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
//		var detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(END_USER_GENERIC_ERROR_MESSAGE).build();
//
//		return handleExceptionInternal(ex, problem, headers, status, request);
//	}
//
//	private ResponseEntity<Object> handleConstraint(ConstraintViolationException ex, HttpHeaders headers, WebRequest request) {
//		var problemType = ProblemType.INVALID_DATA;
//		var status = problemType.getStatus();
//		var detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
//		var problemObjects = ex.getConstraintViolations().stream()
//				.map(objectError -> {
//					var fileName = objectError.getPropertyPath().toString();
//					var userErrorMessage = objectError.getMessage();
//
//					if (StringUtils.contains(fileName, ".")) {
//						var pathNames = StringUtils.split(fileName, ".");
//						fileName = pathNames[pathNames.length - 1];
//					}
//
//					if (objectError instanceof FieldError) {
//						fileName = ((FieldError) objectError).getField();
//					}
//
//
//					return ProblemObject.builder().name(fileName).userMessage(userErrorMessage).build();
//				})
//				.collect(Collectors.toList());
//
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects).build();
//
//		return super.handleExceptionInternal(ex, problem, headers, status, request);
//	}
//
//	private ResponseEntity<Object> handleMissing(MissingServletRequestParameterException ex, HttpHeaders headers, WebRequest request) {
//		var problemType = ProblemType.INVALID_DATA;
//		var status = problemType.getStatus();
//		var detail = "Um ou mais parâmetros obrigatórios não estão presentes.";
//		var problemObject = ProblemObject.builder().name(ex.getParameterName()).userMessage(String.format("Parâmetro '%s' é obrigatório.", ex.getParameterName())).build();
//		var problemObjects = new ArrayList<ProblemObject>();
//
//		problemObjects.add(problemObject);
//
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects).build();
//
//		return super.handleExceptionInternal(ex, problem, headers, status, request);
//	}
//
//	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//
//		var path = joinPath(ex.getPath());
//		var problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
//		var detail = String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente", path);
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(END_USER_GENERIC_ERROR_MESSAGE).build();
//
//		return handleExceptionInternal(ex, problem, headers, status, request);
//
//	}
//
//	private ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
//		var problemType = ProblemType.RESOURCE_NOT_FOUND;
//		var status = problemType.getStatus();
//		var detail = ex.getMessage();
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
//
//		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
//	}
//
//	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//		var path = joinPath(ex.getPath());
//		var problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
//		var detail = String.format(
//				"A propriedade '%s' recebeu o valor '%s', que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s",
//				path, ex.getValue(), ex.getTargetType().getSimpleName());
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(END_USER_GENERIC_ERROR_MESSAGE)
//				.build();
//
//		return handleExceptionInternal(ex, problem, headers, status, request);
//	}
//
//	private ResponseEntity<Object> handleGenericNotFound(MethodArgumentTypeMismatchException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//
//		var problemType = ProblemType.RESOURCE_NOT_FOUND;
//		var detail = problemType.getTitle();
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(problemType.getTitle()).build();
//
//		return handleExceptionInternal(ex, problem, headers, status, request);
//	}
//
//	private boolean isRequiredPathParameterUUID(MethodArgumentTypeMismatchException mismatchException) {
//		return mismatchException.getRequiredType() != null
//				&& mismatchException.getParameter().hasParameterAnnotation(PathVariable.class)
//				&& mismatchException.getRequiredType().equals(UUID.class);
//	}
//
//	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//		var problemType = ProblemType.INVALID_PARAMETER;
//		var detail = String.format(
//				"O parâmetro de URL '%s' recebeu o valor '%s', que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
//				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(END_USER_GENERIC_ERROR_MESSAGE)
//				.build();
//
//		return handleExceptionInternal(ex, problem, headers, status, request);
//	}
//
//	private ResponseEntity<Object> handleValidationException(Exception ex, HttpHeaders headers, HttpStatus status,
//			WebRequest request, BindingResult bindingResult) {
//		var problemType = ProblemType.INVALID_DATA;
//		var detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
//		var problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
//			var message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
//			var name = objectError.getObjectName();
//
//			if (objectError instanceof FieldError) {
//				name = ((FieldError) objectError).getField();
//			}
//
//			return ProblemObject.builder().name(name).userMessage(message).build();
//		}).collect(Collectors.toList());
//
//		var problem = createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects)
//				.build();
//
//		return handleExceptionInternal(ex, problem, headers, status, request);
//	}
//
//	private String joinPath(List<Reference> references) {
//		return references.stream().map(Reference::getFieldName).collect(Collectors.joining("."));
//	}
//
//	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
//		return Problem.builder().timestamp(OffsetDateTime.now()).status(status.value()).type(problemType.getUri())
//				.title(problemType.getTitle()).detail(detail);
//	}
//}
