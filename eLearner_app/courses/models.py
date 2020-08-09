from django.db import models
from django.core.validators import MinValueValidator, MaxValueValidator
# Create your models here.

class Courses(models.Model):
    courseName = models.CharField(max_length=30,blank=False)
    courseGoal = models.CharField(max_length=30,blank=False)
    courseMaxNumberOfLearner = models.IntegerField(blank=False)
    courseOriantationDate = models.DateField(auto_now=False, auto_now_add=False)
    courseStartingDate = models.DateField(auto_now=False, auto_now_add=False)
    courseFinishingDate = models.DateField(auto_now=False, auto_now_add=False)
    courseTotalDuiration = models.CharField(max_length=30,blank=False)
    courseNumberOfClasses = models.CharField(max_length=30,blank=False)
    courseClassDuiration = models.CharField(max_length=30,blank=False)
    courseClassStartTime = models.TimeField(auto_now=False, auto_now_add=False,blank=False)
    courseClassEndTime = models.TimeField(auto_now=False, auto_now_add=False,blank=False)
    courseInstractorId = models.CharField(max_length=50,blank=False)
    courseInstractorName = models.CharField(max_length=30,blank=False)
    courseInstractorQualification = models.CharField(max_length=30,blank=False)
    courseInstractorPhoneNumber = models.CharField(max_length=30,blank=False)
    coursePriceInTk = models.CharField(max_length=30,blank=False)

    def __str__(self):
        return self.courseName



class Learnerns(models.Model):
    course = models.ForeignKey(Courses,on_delete=models.CASCADE)
    learnerId = models.CharField(max_length=50,blank=False)
    isPaymentValified = models.BooleanField(blank=False)


class courseReviews(models.Model):
    course = models.ForeignKey(Courses,on_delete=models.CASCADE)
    learnerId = models.CharField(max_length=50,blank=False)
    review = models.CharField(max_length=30,blank=False)
    star = models.IntegerField(validators=[MinValueValidator(0),MaxValueValidator(5)],blank=False)